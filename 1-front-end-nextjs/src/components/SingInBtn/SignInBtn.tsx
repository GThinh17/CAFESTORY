import Link from "next/link";
import { User } from "lucide-react";
import { Button } from "@/components/ui/button";
import "./SinInBtn.css"

export function SignInButton() {
  return (
   
      <Link href="/login">
        <Button variant="default" className="button">
          <User className="h-4 w-4 p-2" />
          Log In
        </Button>
      </Link>
   
  );
}
