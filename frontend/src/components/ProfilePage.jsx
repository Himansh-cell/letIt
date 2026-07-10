export default function ProfilePage({
  profileForm,
  isBusy,
  onProfileFormChange,
  onSubmit,
  onMakePublic,
  onMakePrivate
}) {
  return (
    <section className="feed-grid">
      <div className="feed-column">
        <div className="panel">
          <h2>Profile</h2>
          <form onSubmit={onSubmit} className="stack">
            <input value={profileForm.name} onChange={(event) => onProfileFormChange({ ...profileForm, name: event.target.value })} placeholder="Name" />
            <select value={profileForm.gender} onChange={(event) => onProfileFormChange({ ...profileForm, gender: event.target.value })}>
              <option value="MALE">Male</option>
              <option value="FEMALE">Female</option>
              <option value="OTHER">Other</option>
              <option value="PREFER_NOT_TO_SAY">Prefer not to say</option>
            </select>
            <input type="date" value={profileForm.dob} onChange={(event) => onProfileFormChange({ ...profileForm, dob: event.target.value })} />
            <input value={profileForm.address} onChange={(event) => onProfileFormChange({ ...profileForm, address: event.target.value })} placeholder="Address" />
            <input value={profileForm.phone} onChange={(event) => onProfileFormChange({ ...profileForm, phone: event.target.value })} placeholder="Phone" />
            <input value={profileForm.photo} onChange={(event) => onProfileFormChange({ ...profileForm, photo: event.target.value })} placeholder="Photo URL" />
            <button type="submit" disabled={isBusy}>Save profile</button>
          </form>
        </div>
      </div>

      <aside className="side-column">
        <div className="panel">
          <h2>Account</h2>
          <div className="row">
            <button onClick={onMakePublic} disabled={isBusy}>Make Public</button>
            <button onClick={onMakePrivate} disabled={isBusy}>Make Private</button>
          </div>
        </div>
      </aside>
    </section>
  );
}
