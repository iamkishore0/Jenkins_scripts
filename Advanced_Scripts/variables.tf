variable "aws_region" {
  default = "us-east-1"
}

variable "cluster_name" {
  default = "my-eks-cluster"
}

variable "availability_zones" {
  type = list(string)
  default = ["us-east-1a", "us-east-1b"]
}
