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
RUN apk add --no-cache curl
COPY --from=builder /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Make nginx listen on port 3000 inside the container
EXPOSE 3000

CMD ["nginx", "-g", "daemon off;"]