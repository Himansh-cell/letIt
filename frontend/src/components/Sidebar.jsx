const tabs = [
  ['feed', 'Feed'],
  ['post', 'Upload'],
  ['social', 'Social'],
  ['requests', 'Follow Requests'],
  ['profile', 'Profile']
];

export default function Sidebar({ activeTab, onTabChange }) {
  return (
    <aside className="rail left-rail">
      <div className="panel brand-box">
        <h1>Letit</h1>
        <p className="muted">Campus social app.</p>
      </div>

      <div className="panel rail-section">
        {tabs.map(([key, label]) => (
          <button key={key} className={activeTab === key ? 'tab active wide-tab' : 'tab wide-tab'} onClick={() => onTabChange(key)}>
            {label}
          </button>
        ))}
      </div>

      <div className="panel rail-section">
        <div className="status-pill">Manual refresh</div>
        <p className="muted">Feed updates only when you click Refresh.</p>
      </div>
    </aside>
  );
}
