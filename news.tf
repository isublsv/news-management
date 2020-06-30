#--------------------------------------------------------
# News Management
#
# Build Web Server
#
# Made By Gartsmanovich Dmitry
#--------------------------------------------------------

provider "aws" {}

# save static ip address for every aws_instance
resource "aws_eip" "my_static_ip" {
  instance = aws_instance.my_webserver.id
}

resource "aws_instance" "my_webserver" {
  ami = "ami-05ca073a83ad2f28c" # Amazon Linux AMI
  instance_type = "t2.micro"
  vpc_security_group_ids = [aws_security_group.my_webserver.id]
  user_data = file("./script/user_data.sh")
  
  #Zero Downtime
  lifecycle {
    create_before_destroy = true
  }

  tags = {
    Name = "Linux Web Server"
    Owner = "isublsv"
    Project = "News Management"
  }
}

resource "aws_security_group" "my_webserver" {
  name        = "Web Server Security Group"
  description = "News Management"

  dynamic "ingress" {
    for_each = [80, 443, 8080]
    content {
      from_port   = ingress.value
      to_port     = ingress.value
      protocol    = "tcp"
      cidr_blocks = ["0.0.0.0/0"]
    }
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "WebServer Security Group"
    Owner = "isublsv"
  }
}
