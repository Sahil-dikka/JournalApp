// src/hooks/useDelete.js
import { useMutation } from "@tanstack/react-query";
import axios from "axios";

// Create a reusable axios instance
const api = axios.create({
  baseURL: "http://localhost:8080",
});

// Attach JWT token
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Hook
export default function useDelete() {
  return useMutation({
    mutationFn: async ({ endpoint }) => {
      const res = await api.delete(endpoint);
      return res.data;
    },
  });
}
