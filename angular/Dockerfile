# stage 1
FROM node:alpine AS build
# WORKDIR /opt/ng
WORKDIR /usr/src/app
COPY package.json package-lock.json ./
RUN npm ci
COPY . .
RUN npm run build:prod

# stage 2
FROM nginx:alpine
COPY nginx/default.conf /etc/nginx/conf.d/default.conf
# COPY --from=build /opt/ng/dist/ng-readme-app /usr/share/nginx/html
COPY --from=build /usr/src/app/dist/ng-readme-app /usr/share/nginx/html
EXPOSE 80