echo "[INFO] dotNet : Going to compile dotNet services..."
cd bank/
echo "[INFO] dotNet : Compiling bank service..."
./compile.sh
echo "[INFO] dotNet : Bank service compilation done."
echo "[INFO] dotNet : Building bank service docker image..."
docker build -t polytechteamc/castexski:teamc_bank .
echo "[INFO] dotNet : Bank docker image built."
cd ../weather
echo "[INFO] dotNet : Compiling weather service..."
./compile.sh
echo "[INFO] dotNet : Weather service compilation done."
echo "[INFO] dotNet : Building Weather service docker image..."
docker build -t polytechteamc/castexski:teamc_weather .
echo "[INFO] dotNet : Weather docker image built."
echo "[INFO] dotNet : dotNet services compilation done."
