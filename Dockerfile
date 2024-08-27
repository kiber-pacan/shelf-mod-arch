FROM eclipse-temurin:22-jdk

WORKDIR /home/build/
COPY ./gradlew .
RUN chmod +x ./gradlew
CMD for i in $(seq 7 $END); do
        sh gradlew clean -Pindex="$i" --no-daemon || true
        sh gradlew build -Pindex="$i" --no-daemon || true
        sh gradlew mergeJars -Pindex="$i" --no-daemon || true
    done
