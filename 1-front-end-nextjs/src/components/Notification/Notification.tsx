import Image from "next/image";
import "../suggestions/suggestions.css";
import { ApiNotification } from "./components/notification-modal";
import { useRouter } from "next/navigation";
import { ApiNotification } from "../NotificationModal";

// UI type
interface NotificationItemProps {
  avatar: string;
  username: string;
  actionText: string;
  date: string;
  onClick?: () => void;
}

const NotificationItem: React.FC<NotificationItemProps> = ({
  avatar,
  username,
  actionText,
  date,
  onClick,
}) => {
  return (
    <div
      className="notification-item flex items-start gap-4 p-3 rounded-xl cursor-pointer hover:bg-muted transition"
      onClick={onClick}
    >
      <Image
        src={avatar}
        alt="Avatar"
        width={48}
        height={48}
        className="avatar-suggest"
      />

      <div className="flex-1 text-sm leading-snug">
        <span className="font-medium">{actionText}</span>
        <div className="text-muted-foreground">{date}</div>
      </div>
    </div>
  );
};

// ---------------- LIST ----------------
const Notifications = ({
  notifications,
  loading,
}: {
  notifications: ApiNotification[];
  loading: boolean;
}) => {
  const router = useRouter();

  if (loading) {
    return <div className="p-4 text-center">Đang tải...</div>;
  }

  if (notifications.length === 0) {
    return <div className="p-4 text-center">Không có thông báo</div>;
  }

  return (
    <div className="w-full max-w-md p-4 space-y-4">
      {notifications
        .slice()

        .map((n) => (
          <NotificationItem
            key={n.id}
            avatar={
              n.senderAvatar ||
              "https://cdn-icons-png.flaticon.com/512/9131/9131529.png"
            }
            username={n.senderName || "System"}
            actionText={n.body || n.title || ""}
            date={new Date(n.createdAt).toLocaleString("vi-VN")}
            onClick={() => {
              if (n.senderId) {
                router.push(`/profile/${n.senderId}`);
              }
            }}
          />
        ))}
    </div>
  );
};

export default Notifications;
