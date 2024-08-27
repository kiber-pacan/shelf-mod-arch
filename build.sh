echo "-------------------------------"
echo "------------BUILDING-----------"
echo "-------------------------------"
mkdir -p buildAllJars | true
y=0

for i in $(seq 7 $END); do
    #sh gradlew clean -Pindex="$y"
    sh gradlew build -Pindex="$y"
    mv ./*/build/libs/shelfmod-mc*.jar "buildAllJars"
    ((y=y+1))
done



echo "-------------------------------"
echo "--------------DONE-------------"
echo "-------------------------------"
