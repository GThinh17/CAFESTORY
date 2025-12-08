export const API = {
    AUTH: {
      LOGIN: "/auth/login",
      REGISTER: "/auth/register",
      REFRESH: "/auth/refresh",
      LOGOUT: "/auth/logout",
    },
  
    USER: {
      ME: "/users/me",
      UPDATE: "/users/update",
    },
  
    WALLET: {
   
    },
  
    BLOG: {
      LIST: "/blogs",
      DETAIL: (id: string) => `/blogs/${id}`,
      CREATE: "/blogs",
      UPDATE: (id: string) => `/blogs/${id}`,
      DELETE: (id: string) => `/blogs/${id}`,
    },
  
    COMMENT: {
      LIST: (blogId: string) => `/blogs/${blogId}/comments`,
      CREATE: (blogId: string) => `/blogs/${blogId}/comments`,
      DELETE: (id: string) => `/comments/${id}`,
    },
  
  };
  