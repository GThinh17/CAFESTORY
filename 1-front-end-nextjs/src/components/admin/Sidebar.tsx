"use client";

import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";

export default function Sidebar() {
  return (
    <div className="space-y-4">
      <Card className="p-4">
        <CardHeader>
          <CardTitle className="text-lg">Admin</CardTitle>
        </CardHeader>
        <CardContent className="space-y-3">
          <div className="flex items-center gap-3">
            <Avatar>
              <AvatarImage src="https://i.pravatar.cc/40?img=12" />
              <AvatarFallback>AD</AvatarFallback>
            </Avatar>
            <div>
              <p className="font-medium">Pham Thanh Vu</p>
              <p className="text-sm text-muted-foreground">Super Admin</p>
            </div>
          </div>

          <nav className="flex flex-col pt-2">
            <Button variant="ghost" className="justify-start w-full">
              Dashboard
            </Button>
            <Button variant="ghost" className="justify-start w-full">
              Users
            </Button>
            <Button variant="ghost" className="justify-start w-full">
              Settings
            </Button>
            <Button variant="ghost" className="justify-start w-full">
              Logs
            </Button>
          </nav>
        </CardContent>
      </Card>

      <Card className="p-4">
        <CardHeader>
          <CardTitle className="text-sm">Quick Actions</CardTitle>
        </CardHeader>
        <CardContent className="flex flex-col gap-2">
          <Button>Create user</Button>
          <Button variant="outline">Export CSV</Button>
        </CardContent>
      </Card>
    </div>
  );
}
