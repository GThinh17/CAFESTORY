import Stats from "@/components/admin/Stats";
import UsersTable from "@/components/admin/UsersTable";

export default function DashboardPage() {
  return (
    <div>
      <h1 className="text-2xl font-bold mb-2">Dashboard</h1>
      <p className="text-sm text-muted-foreground mb-6">
        Overview & management
      </p>

      <Stats total={4} active={2} editors={2} pending={1} />

      <UsersTable />
    </div>
  );
}
