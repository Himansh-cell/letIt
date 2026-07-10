import PostCard from './PostCard.jsx';

export default function FeedPage({
  feed,
  currentUsername,
  isBusy,
  commentDrafts,
  onCommentDraftChange,
  onComment,
  onRefresh,
  onLike,
  onUnlike,
  onPublic,
  onPrivate,
  onDelete
}) {
  return (
    <section className="feed-grid">
      <div className="feed-column">
        <div className="panel">
          <div className="panel-head">
            <h2>Stories</h2>
            <span>{feed.length} posts</span>
          </div>
          <div className="story-row">
            {feed.slice(0, 6).map((post) => (
              <button key={post.id} className="story-chip">@{post.username}</button>
            ))}
          </div>
        </div>

        <div className="panel">
          <div className="panel-head">
            <h2>Feed</h2>
            <button onClick={onRefresh} disabled={isBusy}>Reload</button>
          </div>
          <div className="posts">
            {feed.map((post) => (
              <PostCard
                key={post.id}
                post={post}
                canManage={post.username === currentUsername}
                isBusy={isBusy}
                commentText={commentDrafts[post.id]}
                onCommentTextChange={(text) => onCommentDraftChange((current) => ({ ...current, [post.id]: text }))}
                onComment={onComment}
                onLike={onLike}
                onUnlike={onUnlike}
                onPublic={onPublic}
                onPrivate={onPrivate}
                onDelete={onDelete}
              />
            ))}
            {!feed.length && <p className="muted">No posts loaded. Click Refresh.</p>}
          </div>
        </div>
      </div>

      <aside className="side-column">
        <div className="panel">
          <h2>Recent</h2>
          <div className="recent-list">
            {feed.slice(0, 5).map((post) => (
              <div key={post.id} className="recent-item">
                <strong>@{post.username}</strong>
                <span>{post.caption}</span>
              </div>
            ))}
          </div>
        </div>
      </aside>
    </section>
  );
}
