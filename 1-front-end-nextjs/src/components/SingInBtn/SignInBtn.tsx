import Link from "next/link";
import { User } from "lucide-react";
import { Button } from "@/components/ui/button";

export function SignInButton() {
  return (
    <Link href="/sign-in">
      <Button variant="default" size="sm" className="flex items-center gap-2">
        <User className="h-4 w-4" />
        Sign In
      </Button>
    </Link>
  );
}
