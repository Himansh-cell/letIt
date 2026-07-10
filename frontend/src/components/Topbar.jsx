const titles = {
  feed: 'Home',
  post: 'Upload',
  social: 'Social',
  requests: 'Follow Requests',
  profile: 'Profile'
};

export default function Topbar({ activeTab, isBusy, onRefresh, onLogout }) {
  return (
    <header className="panel topbar">
      <div>
        <h1>{titles[activeTab] || 'Home'}</h1>
        <p className="muted">Simple, not fancy.</p>
      </div>
      <div className="topbar-actions">
        <button onClick={onRefresh} disabled={isBusy}>Refresh</button>
        <button onClick={onLogout} disabled={isBusy}>Logout</button>
      </div>
    </header>
  );
}
