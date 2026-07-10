import { useEffect, useState } from 'react';

export default function UploadPage({ postText, postFile, uploading, onPostTextChange, onPostFileChange, onSubmit }) {
  const [preview, setPreview] = useState('');

  useEffect(() => {
    if (!postFile) {
      setPreview('');
      return undefined;
    }
    const previewUrl = URL.createObjectURL(postFile);
    setPreview(previewUrl);
    return () => URL.revokeObjectURL(previewUrl);
  }, [postFile]);

  return (
    <section className="feed-grid">
      <div className="feed-column">
        <div className="panel">
          <div className="panel-head">
            <h2>New Post</h2>
            <span>{uploading ? 'Uploading...' : 'Ready'}</span>
          </div>
          <form onSubmit={onSubmit} className="stack">
            <input value={postText} onChange={(event) => onPostTextChange(event.target.value)} placeholder="Write a caption" />
            <input type="file" accept="image/*,video/*" onChange={(event) => onPostFileChange(event.target.files?.[0] || null)} />
            {preview ? <img src={preview} alt="" className="draft-preview" /> : <div className="draft-empty">Image preview will appear here</div>}
            <button type="submit" disabled={uploading}>{uploading ? 'Uploading image...' : 'Post'}</button>
          </form>
        </div>
      </div>

      <aside className="side-column">
        <div className="panel">
          <h2>Info</h2>
          <p className="muted">After upload, click Refresh on Feed to load the latest posts.</p>
        </div>
      </aside>
    </section>
  );
}
