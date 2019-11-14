FROM nginx:1.16.1-alpine

LABEL maintainer="kLawrence <lawrence.kim@protonmail.com>"

COPY ./dist /home/
RUN  rm /etc/nginx/conf.d/default.conf

COPY ./conf/default.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
