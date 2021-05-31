echo "Welcome to Castexski service. The building process is going to begin !"
echo "[INFO] Build begins..."


echo "[INFO] build.sh : #############        server        #############"
cd j2e
echo "[INFO] build.sh : Going to build server..."
echo "[INFO] build.sh : Compiling server..."
mvn clean package
echo "[INFO] build.sh : Server compilation done."
echo "[INFO] build.sh : Building server docker image..."
docker build -t polytechteamc/castexski:teamc_j2e .
echo "[INFO] build.sh : Server docker image built."
echo "[INFO] build.sh : #############      server done     #############"

cd ..

echo "[INFO] build.sh : #############        client        #############"
cd client
echo "[INFO] build.sh : Compiling client..."
echo "[INFO] build.sh : Tests are avoided if services are not available."
mvn clean package -DskipTests
echo "[INFO] build.sh : Server compilation done."
echo "[INFO] build.sh : Building client docker image..."
docker build -t polytechteamc/castexski:teamc_client .
echo "[INFO] build.sh : client docker image built."
echo "[INFO] build.sh : #############      client done     #############"

cd ..

echo "[INFO] build.sh : #############   dotNet services    #############"
cd dotNet
./compile.sh
echo "[INFO] build.sh : ############# dotNet services done #############"

cd ..

echo "[INFO] build.sh : Build complete."