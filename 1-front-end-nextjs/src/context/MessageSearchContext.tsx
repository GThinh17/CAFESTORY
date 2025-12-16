"use client";

import { createContext, useContext, useState } from "react";

interface MessageSearchContextType {
  keyword: string;
  setKeyword: (value: string) => void;
}

const MessageSearchContext = createContext<MessageSearchContextType | null>(
  null
);

export function MessageSearchProvider({
  children,
}: {
  children: React.ReactNode;
}) {
  const [keyword, setKeyword] = useState("");

  return (
    <MessageSearchContext.Provider value={{ keyword, setKeyword }}>
      {children}
    </MessageSearchContext.Provider>
  );
}

export function useMessageSearch() {
  const ctx = useContext(MessageSearchContext);
  if (!ctx) {
    throw new Error(
      "useMessageSearch must be used inside MessageSearchProvider"
    );
  }
  return ctx;
}
