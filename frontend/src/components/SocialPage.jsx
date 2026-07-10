import PostCard from './PostCard.jsx';

export default function SocialPage({
  followName,
  usernameLookup,
  usernamePosts,
  isBusy,
  onFollowNameChange,
  onUsernameLookupChange,
  onUserPostsSubmit,
  onFollow
}) {
  return (
    <section className="feed-grid">
      <div className="feed-column">
        <div className="panel">
          <h2>Follow</h2>
          <div className="stack">
            <input value={followName} onChange={(event) => onFollowNameChange(event.target.value)} placeholder="Username" />
            <button onClick={onFollow} disabled={isBusy || !followName.trim()}>Send request</button>
          </div>
        </div>

        <div className="panel">
          <h2>User Posts</h2>
          <form onSubmit={onUserPostsSubmit} className="row">
            <input value={usernameLookup} onChange={(event) => onUsernameLookupChange(event.target.value)} placeholder="Username" />
            <button type="submit" disabled={isBusy}>Load</button>
          </form>
          <div className="posts">
            {usernamePosts.map((post) => (
              <PostCard key={post.id} post={post} isBusy={isBusy} compact />
            ))}
          </div>
        </div>
      </div>
    </section>
  );
}
