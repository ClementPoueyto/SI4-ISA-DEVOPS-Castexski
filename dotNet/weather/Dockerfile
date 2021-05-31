FROM mono:latest
ADD ./src /src
RUN mcs /src/*.cs -pkg:wcf -out:server.exe
EXPOSE 9091

ENTRYPOINT [ "mono",  "./server.exe" ]
CMD ["/standalone"]