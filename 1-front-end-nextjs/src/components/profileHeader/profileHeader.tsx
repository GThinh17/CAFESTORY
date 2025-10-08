import Image from "next/image";
import styles from "./profileHeader.module.css";

interface ProfileHeaderProps {
  username: string;
  verified?: boolean;
  following: boolean;
  posts: number;
  followers: string;
  followingCount: number;
  name: string;
  bio: string;
  website?: string;
  avatar: string;
}

export function ProfileHeader({
  username,
  verified,
  following,
  posts,
  followers,
  followingCount,
  name,
  bio,
  website,
  avatar,
}: ProfileHeaderProps) {
  return (
    <div className={styles.profileHeader}>
      {/* Avatar */}
      <div className={styles.profileAvatar}>
        <Image
          src={avatar}
          alt={username}
          width={150}
          height={150}
          className={styles.avatarImg}
        />
      </div>

      {/* Info */}
      <div className={styles.profileInfo}>
        <div className={styles.profileTop}>
          <h2 className={styles.username}>{username}</h2>
          {verified && <span className={styles.verified}>✔️</span>}
          <button className={`${styles.btn} ${styles.followBtn}`}>
            {following ? "Following" : "Follow"}
          </button>
          <button className={`${styles.btn} ${styles.messageBtn}`}>
            Message
          </button>
          <button className={`${styles.btn} ${styles.moreBtn}`}>⋯</button>
        </div>

        <div className={styles.profileStats}>
          <span>
            <strong>{posts}</strong> posts
          </span>
          <span>
            <strong>{followers}</strong> followers
          </span>
          <span>
            <strong>{followingCount}</strong> following
          </span>
        </div>

        <div className={styles.profileBio}>
          <strong>{name}</strong>
          <p>{bio}</p>
          {website && (
            <a
              href={website}
              className={styles.website}
              target="_blank"
              rel="noreferrer"
            >
              {website}
            </a>
          )}
        </div>
      </div>
    </div>
  );
}
