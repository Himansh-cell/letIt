export default function AuthPage({
  authMode,
  authForm,
  loading,
  message,
  onAuthModeChange,
  onAuthFormChange,
  onSubmit
}) {
  return (
    <div className="page auth-page">
      <div className="auth-box">
        <h1>Letit</h1>
        <p className="muted">Simple social app frontend connected to the backend API.</p>

        <div className="tabs">
          <button className={authMode === 'login' ? 'tab active' : 'tab'} onClick={() => onAuthModeChange('login')}>Login</button>
          <button className={authMode === 'register' ? 'tab active' : 'tab'} onClick={() => onAuthModeChange('register')}>Register</button>
        </div>

        <form onSubmit={onSubmit} className="stack">
          {authMode === 'register' && (
            <input value={authForm.userName} onChange={(event) => onAuthFormChange({ ...authForm, userName: event.target.value })} placeholder="Username" />
          )}
          <input value={authForm.email} onChange={(event) => onAuthFormChange({ ...authForm, email: event.target.value })} placeholder="Email" />
          <input type="password" value={authForm.password} onChange={(event) => onAuthFormChange({ ...authForm, password: event.target.value })} placeholder="Password" />
          <button type="submit" disabled={loading}>{loading ? 'Please wait' : authMode === 'login' ? 'Login' : 'Register'}</button>
        </form>

        {message && <p className="message">{message}</p>}
      </div>
    </div>
  );
}
