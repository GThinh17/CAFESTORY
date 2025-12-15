// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import { getMessaging } from "firebase/messaging";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyCClt2oeEGi1hJJ3RbDPxZGm_37MfzOtak",
  authDomain: "cafestorychat.firebaseapp.com",
  databaseURL: "https://cafestorychat-default-rtdb.asia-southeast1.firebasedatabase.app",
  projectId: "cafestorychat",
  storageBucket: "cafestorychat.firebasestorage.app",
  messagingSenderId: "530100184149",
  appId: "1:530100184149:web:8419fbb59d666c0dd4e0dc",
  measurementId: "G-ZRC21N2M9R"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
export { app };