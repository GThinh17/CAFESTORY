"use client";

import { createContext, useContext, useEffect, useState } from "react";

export interface User {
  id: string;
  username: string;
  avatar?: string;
  role?: [];
}

interface AuthContextType {
  user: User | null;
  token: string | null;
  login: (token: string, user: User) => void;
  logout: () => void;
  loading: boolean;
}

const AuthContext = createContext<AuthContextType | null>(null);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState<string | null>(null);
  const [loading, setLoading] = useState<boolean>(true);

  // Load từ localStorage
  useEffect(() => {
    const savedUser = localStorage.getItem("user");
    const savedToken = localStorage.getItem("token");
    if (savedToken && savedUser) {
      setUser(JSON.parse(savedUser));
      setToken(savedToken);
    }

    setLoading(false);
  }, [token]);

  const login = (savedToken: string, savedUser: User) => {
    localStorage.setItem("token", savedToken);
    localStorage.setItem("user", JSON.stringify(savedUser));

    setToken(savedToken);
    setUser(savedUser);
  };

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("user");

    setToken(null);
    setUser(null);
  };

  const value: AuthContextType = {
    user,
    token,
    login,
    logout,
    loading,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

// Hook dùng trong component
export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error("useAuth phải nằm trong <AuthProvider>");
  return ctx;
}
