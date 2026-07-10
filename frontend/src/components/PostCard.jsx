export default function PostCard({
  post,
  isBusy,
  commentText,
  onCommentTextChange,
  onComment,
  onLike,
  onUnlike,
  onPublic,
  onPrivate,
  onDelete,
  canManage = false,
  compact = false
}) {
  return (
    <article className="post">
      <div className="post-head">
        <strong>@{post.username}</strong>
        {post.visibility && <span>{post.visibility}</span>}
      </div>

      {post.mediaUrls?.length ? (
        <div className="media-grid">
          {post.mediaUrls.map((src) => (
            <img key={src} src={`${src}?post=${post.id}`} alt="" className="post-image" loading="lazy" />
          ))}
        </div>
      ) : null}

      <p>{post.caption}</p>

      {!compact && (
        <>
          <div className="meta">
            <span>{post.likesCount ?? 0} likes</span>
            <span>{post.commentsCount ?? 0} comments</span>
          </div>
          <div className="row">
            <button onClick={() => onLike(post.id)} disabled={isBusy}>Like</button>
            <button onClick={() => onUnlike(post.id)} disabled={isBusy}>Unlike</button>
            {canManage && (
              <>
                <button onClick={() => onPublic(post.id)} disabled={isBusy}>Public</button>
                <button onClick={() => onPrivate(post.id)} disabled={isBusy}>Private</button>
                <button onClick={() => onDelete(post.id)} disabled={isBusy}>Delete</button>
              </>
            )}
          </div>
          <div className="comments-box">
            <input value={commentText || ''} onChange={(event) => onCommentTextChange(event.target.value)} placeholder="Write a comment" />
            <button onClick={() => onComment(post.id)} disabled={isBusy}>Send</button>
          </div>
        </>
      )}
    </article>
  );
}
