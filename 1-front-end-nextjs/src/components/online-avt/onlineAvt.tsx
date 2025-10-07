import Image from "next/image";
import "./onlineAvt.css";

const users = [
  {
    name: "itsdougthepu",
    avatar: "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
  },
  {
    name: "openaidalle",
    avatar: "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
  },
  {
    name: "lewishamilton",
    avatar: "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
  },
  {
    name: "wahab.xyz",
    avatar: "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
  },
  {
    name: "defavours",
    avatar: "https://cdn-icons-png.flaticon.com/512/9131/9131529.png",
  },
  { name: "mkbhd", avatar: "https://cdn-icons-png.flaticon.com/512/9131/9131529.png" },
];

export function OnlineAvt() {
  return (
    <div className="container">
      {users.map((user) => (
        <div key={user.name} className="story">
          <div className="avatarWrapper">
            <Image
              src={user.avatar}
              alt={user.name}
              width={50}
              height={50}
              className="avatar"
            />
          </div>
          <span className="username">{user.name}</span>
        </div>
      ))}
    </div>
  );
}
