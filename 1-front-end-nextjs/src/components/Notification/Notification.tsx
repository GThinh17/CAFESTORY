import Image from "next/image";

export default function Notifications() {
  return (
    <div className="w-full max-w-md p-4 space-y-4">
      <h2 className="text-xl font-semibold">Notifications</h2>

      <div className="space-y-4">
        {/* Item */}
        <div className="flex items-start gap-3 p-3 rounded-xl bg-card">          
          <Image
            src="/avatar.png"
            alt="Avatar"
            width={48}
            height={48}
            className="rounded-full"
          />

          <div className="flex-1 text-sm leading-snug">
            <span className="font-medium">ferdy.lindsgnferdy.lindsgn</span> replied to your comment on ferdy.lindsgn's post: 
            <span className="text-primary">@thnhvu_2</span> 🔥🔥
            <div className="text-muted-foreground">Oct 09</div>
          </div>

          <Image
            src="/thumbnail.png"
            alt="Post thumbnail"
            width={48}
            height={48}
            className="rounded-md object-cover"
          />
        </div>

        {/* Item */}
        <div className="flex items-start gap-3 p-3 rounded-xl bg-card">
          <Image
            src="/avatar.png"
            alt="Avatar"
            width={48}
            height={48}
            className="rounded-full"
          />

          <div className="flex-1 text-sm leading-snug">
            <span className="font-medium">ferdy.lindsgn</span> liked your comment: 😍😍😍
            <div className="text-muted-foreground">Oct 09</div>
          </div>

          <Image
            src="/thumbnail.png"
            alt="Post thumbnail"
            width={48}
            height={48}
            className="rounded-md object-cover"
          />
        </div>
      </div>
    </div>
  );
}
