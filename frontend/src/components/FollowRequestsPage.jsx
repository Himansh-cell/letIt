export default function FollowRequestsPage({ requests, isBusy, onLoad, onAccept }) {
  return (
    <section className="feed-grid">
      <div className="feed-column">
        <div className="panel">
          <div className="panel-head">
            <h2>My Follow Requests</h2>
            <button onClick={onLoad} disabled={isBusy}>Load Requests</button>
          </div>

          <div className="posts">
            {requests.map((request, index) => (
              <div key={`${request.followerUsername}-${index}`} className="request-row">
                <div>
                  <strong>@{request.followerUsername}</strong>
                  <p className="muted">wants to follow you</p>
                </div>
                <button onClick={() => onAccept(request.followerUsername)} disabled={isBusy}>Accept</button>
              </div>
            ))}
            {!requests.length && <p className="muted">No pending requests. Click Load Requests to check again.</p>}
          </div>
        </div>
      </div>
    </section>
  );
}
