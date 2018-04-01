all:
	docker build . -f Dockerfile.base -t mob-ai-java
	docker build . -f Dockerfile.builder -t robot-$(BOT_NAME) --network none --build-arg BOT=$(BOT_TYPE)
