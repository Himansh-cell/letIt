import { useEffect, useState } from 'react';
import { api, authStorage } from './api';
import AuthPage from './components/AuthPage.jsx';
import FeedPage from './components/FeedPage.jsx';
import FollowRequestsPage from './components/FollowRequestsPage.jsx';
import ProfilePage from './components/ProfilePage.jsx';
import Sidebar from './components/Sidebar.jsx';
import SocialPage from './components/SocialPage.jsx';
import Topbar from './components/Topbar.jsx';
import UploadPage from './components/UploadPage.jsx';

const blankRegister = { userName: '', email: '', password: '' };
const blankProfile = {
  name: '',
  gender: 'MALE',
  dob: '',
  address: '',
  phone: '',
  photo: ''
};

export default function App() {
  const [token, setToken] = useState(authStorage.getToken());
  const [activeTab, setActiveTab] = useState('feed');
  const [authMode, setAuthMode] = useState('login');
  const [authForm, setAuthForm] = useState(blankRegister);
  const [postText, setPostText] = useState('');
  const [postFile, setPostFile] = useState(null);
  const [commentDrafts, setCommentDrafts] = useState({});
  const [followName, setFollowName] = useState('');
  const [feed, setFeed] = useState([]);
  const [usernamePosts, setUsernamePosts] = useState([]);
  const [usernameLookup, setUsernameLookup] = useState('');
  const [followRequests, setFollowRequests] = useState([]);
  const [profileForm, setProfileForm] = useState(blankProfile);
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(false);
  const [uploading, setUploading] = useState(false);

  const isAuthed = Boolean(token);
  const isBusy = loading || uploading;
  const currentUsername = authStorage.getUsername(token);

  useEffect(() => {
    if (isAuthed) {
      loadFeed();
    }
  }, [isAuthed]);

  async function withStatus(action) {
    setMessage('');
    setLoading(true);
    try {
      return await action();
    } catch (error) {
      setMessage(error.message || 'Something went wrong');
      return null;
    } finally {
      setLoading(false);
    }
  }

  async function loadFeed() {
    const data = await withStatus(() => api.feed());
    if (data) setFeed(data.content || []);
  }

  async function loadFollowRequests() {
    const data = await withStatus(() => api.followRequests());
    if (data) setFollowRequests(data || []);
  }

  async function handleRefresh() {
    if (activeTab === 'requests') {
      await loadFollowRequests();
      return;
    }
    if (activeTab === 'social') {
      if (usernameLookup.trim()) {
        await handleUserPosts();
      }
      return;
    }
    await loadFeed();
  }

  async function handleAuthSubmit(event) {
    event.preventDefault();
    const result = await withStatus(() =>
      authMode === 'login' ? api.login(authForm.email, authForm.password) : api.register(authForm)
    );
    if (result?.token) {
      authStorage.setToken(result.token);
      setToken(result.token);
      setActiveTab('feed');
    } else if (authMode === 'register' && result !== null) {
      setMessage('Registered. Now log in.');
      setAuthMode('login');
    }
  }

  async function handleCreatePost(event) {
    event.preventDefault();
    if (!postFile) {
      setMessage('Pick an image first');
      return;
    }

    setUploading(true);
    setMessage('');
    try {
      const formData = new FormData();
      formData.append('file', postFile);
      formData.append('caption', postText);
      await api.createPost(formData);
      setPostText('');
      setPostFile(null);
      event.target.reset();
      setMessage('Image uploaded. Click Refresh to see latest feed.');
    } catch (error) {
      setMessage(error.message || 'Upload failed');
    } finally {
      setUploading(false);
    }
  }

  async function handleComment(postId) {
    const text = commentDrafts[postId]?.trim();
    if (!text) return;
    const result = await withStatus(() => api.commentPost(postId, text));
    if (result !== null) {
      setCommentDrafts((current) => ({ ...current, [postId]: '' }));
    }
  }

  async function handleUserPosts(event) {
    if (event) event.preventDefault();
    if (!usernameLookup.trim()) return;
    const data = await withStatus(() => api.postsByUser(usernameLookup.trim()));
    if (data) setUsernamePosts(data || []);
  }

  async function handleProfileCreate(event) {
    event.preventDefault();
    const result = await withStatus(() => api.createProfile(profileForm));
    if (result !== null) setMessage('Profile saved');
  }

  async function handleAcceptRequest(username) {
    const result = await withStatus(() => api.acceptFollow(username));
    if (result !== null) loadFollowRequests();
  }

  if (!isAuthed) {
    return (
      <AuthPage
        authMode={authMode}
        authForm={authForm}
        loading={loading}
        message={message}
        onAuthModeChange={setAuthMode}
        onAuthFormChange={setAuthForm}
        onSubmit={handleAuthSubmit}
      />
    );
  }

  return (
    <div className="page shell">
      <Sidebar activeTab={activeTab} onTabChange={setActiveTab} />

      <main className="main-column">
        <Topbar
          activeTab={activeTab}
          isBusy={isBusy}
          onRefresh={handleRefresh}
          onLogout={() => {
            authStorage.clear();
            setToken('');
          }}
        />

        {message && <div className="message">{message}</div>}

        {activeTab === 'feed' && (
          <FeedPage
            feed={feed}
            currentUsername={currentUsername}
            isBusy={isBusy}
            commentDrafts={commentDrafts}
            onCommentDraftChange={setCommentDrafts}
            onComment={handleComment}
            onRefresh={loadFeed}
            onLike={(postId) => api.likePost(postId).then(loadFeed)}
            onUnlike={(postId) => api.unlikePost(postId).then(loadFeed)}
            onPublic={(postId) => api.setVisibility(postId, 'PUBLIC').then(loadFeed)}
            onPrivate={(postId) => api.setVisibility(postId, 'PRIVATE').then(loadFeed)}
            onDelete={(postId) => api.deletePost(postId).then(loadFeed)}
          />
        )}

        {activeTab === 'post' && (
          <UploadPage
            postText={postText}
            postFile={postFile}
            uploading={uploading}
            onPostTextChange={setPostText}
            onPostFileChange={setPostFile}
            onSubmit={handleCreatePost}
          />
        )}

        {activeTab === 'social' && (
          <SocialPage
            followName={followName}
            usernameLookup={usernameLookup}
            usernamePosts={usernamePosts}
            isBusy={isBusy}
            onFollowNameChange={setFollowName}
            onUsernameLookupChange={setUsernameLookup}
            onUserPostsSubmit={handleUserPosts}
            onFollow={() => api.follow(followName).then(() => setMessage('Follow request sent'))}
          />
        )}

        {activeTab === 'requests' && (
          <FollowRequestsPage
            requests={followRequests}
            isBusy={isBusy}
            onLoad={loadFollowRequests}
            onAccept={handleAcceptRequest}
          />
        )}

        {activeTab === 'profile' && (
          <ProfilePage
            profileForm={profileForm}
            isBusy={isBusy}
            onProfileFormChange={setProfileForm}
            onSubmit={handleProfileCreate}
            onMakePublic={() => api.makePublic().then(() => setMessage('Account public'))}
            onMakePrivate={() => api.makePrivate().then(() => setMessage('Account private'))}
          />
        )}
      </main>
    </div>
  );
}
