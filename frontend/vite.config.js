import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    proxy: {
      '/auth': 'http://localhost:8080',
      '/post': 'http://localhost:8080',
      '/comment': 'http://localhost:8080',
      '/follow': 'http://localhost:8080',
      '/like': 'http://localhost:8080',
      '/account': 'http://localhost:8080',
      '/profile': 'http://localhost:8080',
      '/media': 'http://localhost:8080'
    }
  }
});
