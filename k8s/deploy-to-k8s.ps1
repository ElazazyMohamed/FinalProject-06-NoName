# PowerShell script for deploying microservices to Kubernetes/Minikube
# Enhanced deployment script with error handling and debugging

# Function to check if a command exists
function Test-CommandExists {
  param ($command)
  $exists = $null -ne (Get-Command $command -ErrorAction SilentlyContinue)
  return $exists
}

# Function to check if minikube is running
function Test-MinikubeRunning {
  $status = minikube status
  return $status -match "host: Running"
}

Write-Host "===== Checking prerequisites =====" -ForegroundColor Cyan
# Check if kubectl is installed
if (-not (Test-CommandExists "kubectl")) {
  Write-Host "Error: kubectl is not installed. Please install kubectl first." -ForegroundColor Red
  exit 1
}

# Check if minikube is installed
if (-not (Test-CommandExists "minikube")) {
  Write-Host "Error: minikube is not installed. Please install minikube first." -ForegroundColor Red
  exit 1
}

# Check if docker is installed
if (-not (Test-CommandExists "docker")) {
  Write-Host "Error: docker is not installed. Please install docker first." -ForegroundColor Red
  exit 1
}

Write-Host "===== Starting Minikube =====" -ForegroundColor Cyan
if (-not (Test-MinikubeRunning)) {
  minikube start --driver=docker
} else {
  Write-Host "Minikube is already running." -ForegroundColor Green
}

Write-Host "===== Configuring Docker to use Minikube daemon =====" -ForegroundColor Cyan
# For Windows PowerShell, need different approach
Write-Host "NOTE: On Windows, you may need to manually set Docker context to minikube." -ForegroundColor Yellow
Write-Host "Run: & minikube -p minikube docker-env | Invoke-Expression" -ForegroundColor Yellow
& minikube -p minikube docker-env | Invoke-Expression

Write-Host "===== Creating k8s directory if it doesn't exist =====" -ForegroundColor Cyan
if (-not (Test-Path -Path "k8s")) {
  New-Item -ItemType Directory -Path "k8s" | Out-Null
}

Write-Host "===== Building Docker images =====" -ForegroundColor Cyan
# Check if Dockerfiles exist before building
$services = @("user", "tag", "notification", "reminder", "notes", "logging", "gateway")

foreach ($service in $services) {
  if (Test-Path -Path "./${service}/Dockerfile") {
    Write-Host "Building ${service}..." -ForegroundColor Green
    docker build -t ${service}:latest ./${service}
    if (-not $?) {
      Write-Host "Error building ${service} image. Check Dockerfile in ./${service}" -ForegroundColor Red
      exit 1
    }
  } else {
    Write-Host "Warning: Dockerfile not found for ${service}. Skipping..." -ForegroundColor Yellow
  }
}

Write-Host "===== Deploying databases and message brokers =====" -ForegroundColor Cyan
# Apply each yaml file if it exists
$resources = @("01-postgres", "02-mongodb", "03-redis", "04-rabbitmq")

foreach ($resource in $resources) {
  if (Test-Path -Path "./k8s/${resource}.yaml") {
    Write-Host "Applying ${resource}..." -ForegroundColor Green
    kubectl apply -f ./k8s/${resource}.yaml
    if (-not $?) {
      Write-Host "Error applying ${resource}.yaml" -ForegroundColor Red
      exit 1
    }
  } else {
    Write-Host "Warning: ${resource}.yaml not found. Skipping..." -ForegroundColor Yellow
  }
}

Write-Host "===== Waiting for databases to initialize (30 seconds) =====" -ForegroundColor Cyan
Write-Host "This may take a while. Please be patient..." -ForegroundColor Yellow
Start-Sleep -Seconds 30

Write-Host "===== Checking database pods status =====" -ForegroundColor Cyan
kubectl get pods | Select-String -Pattern "postgres|mongodb|redis|rabbitmq"

Write-Host "===== Deploying microservices =====" -ForegroundColor Cyan
# Apply each yaml file if it exists
$serviceYamls = @("10-user", "11-tag", "12-notification", "13-reminder", "14-notes", "15-logging", "16-gateway")

foreach ($serviceYaml in $serviceYamls) {
  if (Test-Path -Path "./k8s/${serviceYaml}.yaml") {
    Write-Host "Applying ${serviceYaml}..." -ForegroundColor Green
    kubectl apply -f ./k8s/${serviceYaml}.yaml
    if (-not $?) {
      Write-Host "Error applying ${serviceYaml}.yaml" -ForegroundColor Red
      exit 1
    }
  } else {
    Write-Host "Warning: ${serviceYaml}.yaml not found. Skipping..." -ForegroundColor Yellow
  }
}

Write-Host "===== Deployment complete! =====" -ForegroundColor Green
Write-Host "===== Checking pod status =====" -ForegroundColor Cyan
kubectl get pods

Write-Host "===== Service endpoints =====" -ForegroundColor Cyan
kubectl get svc

# Display connection info
Write-Host "===== Access your services with: =====" -ForegroundColor Cyan
foreach ($service in $services) {
  $svcExists = kubectl get svc ${service} 2>$null
  if ($svcExists) {
    Write-Host "minikube service ${service}   (for ${service} service)" -ForegroundColor Green
  } else {
    Write-Host "Warning: ${service} service not found." -ForegroundColor Yellow
  }
}

Write-Host ""
Write-Host "===== TROUBLESHOOTING =====" -ForegroundColor Yellow
Write-Host "If any pods show errors, check logs with:"
Write-Host "kubectl logs <pod-name>" -ForegroundColor Cyan
Write-Host ""
Write-Host "For more details about a pod's status:"
Write-Host "kubectl describe pod <pod-name>" -ForegroundColor Cyan 