import Image from "next/image";
import "../suggestions/suggestions.css";
import { ApiNotification } from "./components/notification-modal";

// UI type
interface NotificationItemProps {
  avatar: string;
  username: string;
  actionText: string;
  tag?: string;
  date: string;
  thumbnail?: string;
}

// Item component (GIỮ NGUYÊN)
const NotificationItem: React.FC<NotificationItemProps> = ({
  avatar,
  username,
  actionText,
  tag,
  date,
  thumbnail,
}) => {
  return (
    <div className="notification-item flex items-start gap-4 p-3 rounded-xl">
      <Image
        src={avatar}
        alt="Avatar"
        width={48}
        height={48}
        className="avatar-suggest"
      />

      <div className="flex-1 text-sm leading-snug">
        <span className="font-medium">{username}</span> {actionText}{" "}
        {tag && <span className="text-primary">{tag}</span>}
        <div className="text-muted-foreground">{date}</div>
      </div>

      {thumbnail && (
        <Image
          src={thumbnail}
          alt="Post thumbnail"
          width={48}
          height={48}
          className="rounded-md object-cover h-8 w-12"
        />
      )}
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
  if (loading) {
    return <div className="p-4 text-center">Đang tải...</div>;
  }

  if (notifications.length === 0) {
    return <div className="p-4 text-center">Không có thông báo</div>;
  }

  const mapped = [...notifications].reverse().map((n) => ({
    username: n.senderName ?? "Thông báo: ",
    actionText: n.title,
    date: new Date(n.createdAt).toLocaleDateString("vi-VN"),
  }));

  return (
    <div className="w-full max-w-md p-4 space-y-4">
      <div className="space-y-4">
        {mapped.map((item, index) => (
          <NotificationItem key={index} {...item} />
        ))}
      </div>
    </div>
  );
};

export default Notifications;
