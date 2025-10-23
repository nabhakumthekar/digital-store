FROM node:20.11.1
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN test -f public/index.html
EXPOSE 3000
CMD ["npm", "start"]
