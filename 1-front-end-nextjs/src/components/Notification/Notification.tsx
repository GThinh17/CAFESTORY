import Image from "next/image";
import "../suggestions/suggestions.css";
// Định nghĩa type cho 1 notification
interface NotificationItemProps {
  avatar: string;
  username: string;
  actionText: string;
  tag?: string;
  date: string;
  thumbnail?: string;
}

// Component riêng cho 1 notification item
const NotificationItem: React.FC<NotificationItemProps> = ({
  avatar,
  username,
  actionText,
  tag,
  date,
  thumbnail,
}) => {
  return (
    <div className="notification-item flex items-start gap-4 p-3 rounded-xl ">
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

const Notifications: React.FC = () => {
  // List data
  const notifications: NotificationItemProps[] = [
    {
      avatar: "/testPost.jpg",
      username: "ferdy.lindsgnferdy.lindsgn",
      actionText: "replied to your comment on ferdy.lindsgn's post:",
      tag: "@thnhvu_2 🔥🔥",
      date: "Oct 09",
      thumbnail: "/testPost.jpg",
    },
    {
      avatar: "/testPost.jpg",
      username: "ferdy.lindsgn",
      actionText: "liked your comment: 😍😍😍",
      date: "Oct 09",
      thumbnail: "/testPost.jpg",
    },
    {
      avatar: "/testPost.jpg",
      username: "ferdy.lindsgn",
      actionText: "liked your comment: 😍😍😍",
      date: "Oct 09",
      thumbnail: "/testPost.jpg",
    },
    {
      avatar: "/testPost.jpg",
      username: "ferdy.lindsgn",
      actionText: "liked your comment: 😍😍😍",
      date: "Oct 09",
      thumbnail: "/testPost.jpg",
    },
    {
      avatar: "/testPost.jpg",
      username: "ferdy.lindsgn",
      actionText: "liked your comment: 😍😍😍",
      date: "Oct 09",
      thumbnail: "/testPost.jpg",
    },
    {
      avatar: "/testPost.jpg",
      username: "ferdy.lindsgn",
      actionText: "liked your comment: 😍😍😍",
      date: "Oct 09",
      thumbnail: "/testPost.jpg",
    },
    {
      avatar: "/testPost.jpg",
      username: "ferdy.lindsgn",
      actionText: "liked your comment: 😍😍😍",
      date: "Oct 09",
      thumbnail: "/testPost.jpg",
    },
    {
      avatar: "/testPost.jpg",
      username: "ferdy.lindsgn",
      actionText: "liked your comment: 😍😍😍",
      date: "Oct 09",
      thumbnail: "/testPost.jpg",
    },
    {
      avatar: "/testPost.jpg",
      username: "ferdy.lindsgn",
      actionText: "liked your comment: 😍😍😍",
      date: "Oct 09",
      thumbnail: "/testPost.jpg",
    },
  ];

  return (
    <div className="w-full max-w-md p-4 space-y-4 ">
      <div className="space-y-4">
        {notifications.map((item, index) => (
          <NotificationItem key={index} {...item} />
        ))}
      </div>
    </div>
  );
};

export default Notifications;
