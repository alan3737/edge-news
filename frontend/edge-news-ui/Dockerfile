# Stage 1: Build the React application
FROM node:22-alpine AS builder
WORKDIR /app

# Copy dependency definitions and install them first (for better caching)
COPY package*.json ./
RUN npm install

# Copy the rest of the source code and build
COPY . .
RUN npm run build

# Stage 2: Serve the app using a lightweight web server (Nginx)
FROM nginx:alpine
COPY --from=builder /app/dist /usr/share/nginx/html

# Expose port 3000 to match your docker-compose setup
EXPOSE 3000

CMD ["nginx", "-g", "daemon off;"]