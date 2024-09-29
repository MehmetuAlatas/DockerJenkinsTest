# Jenkins'in son sürümü tabanlı imajdan başla
# jenkins/jenkins:2.430-jdk21 te yapilabilir
FROM jenkins/jenkins:2.430-jdk21

# Root kullanıcısına geçiş yap
USER root

# Java 21 ve Maven 3.9.6 kurulumunu yap
RUN apt-get update && \
    apt-get install -y wget gnupg && \
    wget -O /tmp/maven.tar.gz https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz && \
    tar xzf /tmp/maven.tar.gz -C /opt && \
    ln -s /opt/apache-maven-3.9.6 /opt/maven && \
    rm -f /tmp/maven.tar.gz && \
    echo "export M2_HOME=/opt/maven" >> /etc/profile.d/maven.sh && \
    echo "export PATH=\${M2_HOME}/bin:\${PATH}" >> /etc/profile.d/maven.sh && \
    chmod +x /etc/profile.d/maven.sh && \
    apt-get install -y openjdk-21-jdk && \
    apt-get clean

# Ortam değişkenlerini ayarla
ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
ENV MAVEN_HOME=/opt/maven
ENV PATH=${MAVEN_HOME}/bin:${PATH}

# Jenkins user'a geri dön
USER jenkins
