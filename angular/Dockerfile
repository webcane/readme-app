# stage 1
FROM node:alpine AS build
ARG BUILD_CMD=build:prod
RUN apk add --update python3 make g++ && rm -rf /var/cache/apk/*
# WORKDIR /opt/ng
WORKDIR /usr/src/app
COPY package.json package-lock.json ./
RUN npm ci
COPY . .
RUN npm run $BUILD_CMD

# stage 2
FROM nginx:alpine
COPY nginx/default.conf /etc/nginx/conf.d/default.conf
# COPY --from=build /opt/ng/dist/ng-readme-app /usr/share/nginx/html
COPY --from=build /usr/src/app/dist/ng-readme-app /usr/share/nginx/html
EXPOSE 80
