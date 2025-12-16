importScripts("https://www.gstatic.com/firebasejs/10.7.0/firebase-app-compat.js");
importScripts("https://www.gstatic.com/firebasejs/10.7.0/firebase-messaging-compat.js");

firebase.initializeApp({
  apiKey: "AIzaSyCClt2oeEGi1hJJ3RbDPxZGm_37MfzOtak",
  authDomain: "cafestorychat.firebaseapp.com",
  projectId: "cafestorychat",
  messagingSenderId: "530100184149",
  appId: "1:530100184149:web:8419fbb59d666c0dd4e0dc"
});

const messaging = firebase.messaging();

messaging.onBackgroundMessage((payload) => {
  console.log("[firebase-messaging-sw.js] Received background message", payload);

  const { title, body } = payload.notification || {};

  self.registration.showNotification(title || "Thông báo", {
    body: body || "",
    data: payload.data
  });
});
