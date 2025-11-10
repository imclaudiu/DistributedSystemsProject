# Stage 1: Build the static files
# Use a Node.js image to build your app
FROM node:18-alpine as builder
WORKDIR /app
COPY package.json .
COPY package-lock.json .
RUN npm install
COPY . .
RUN npm run build

# Stage 2: Serve the static files
# Use a tiny Nginx server
FROM nginx:1.25-alpine
# Copy the built files from the 'builder' stage
COPY --from=builder /app/dist /usr/share/nginx/html
# Nginx will automatically serve the index.html on port 80
EXPOSE 80