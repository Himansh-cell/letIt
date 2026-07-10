const TOKEN_KEY = 'letit_token';

export const authStorage = {
  getToken() {
    return localStorage.getItem(TOKEN_KEY) || '';
  },
  setToken(token) {
    localStorage.setItem(TOKEN_KEY, token);
  },
  clear() {
    localStorage.removeItem(TOKEN_KEY);
  },
  getUsername(token = this.getToken()) {
    if (!token) return '';
    try {
      const payload = token.split('.')[1];
      const decoded = JSON.parse(atob(payload.replace(/-/g, '+').replace(/_/g, '/')));
      return decoded.sub || '';
    } catch {
      return '';
    }
  }
};

async function request(path, options = {}) {
  const headers = new Headers(options.headers || {});
  const token = authStorage.getToken();
  if (token && !headers.has('Authorization')) {
    headers.set('Authorization', `Bearer ${token}`);
  }

  const response = await fetch(path, {
    ...options,
    headers
  });

  if (!response.ok) {
    const text = await response.text();
    throw new Error(text || `Request failed: ${response.status}`);
  }

  const contentType = response.headers.get('content-type') || '';
  if (contentType.includes('application/json')) {
    return response.json();
  }
  return response.text();
}

export const api = {
  login(email, password) {
    const params = new URLSearchParams({ email, password });
    return request(`/auth/login?${params.toString()}`, { method: 'POST' });
  },
  register(body) {
    return request('/auth/register', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body)
    });
  },
  feed(page = 0, size = 20) {
    return request(`/post/feed?page=${page}&size=${size}`);
  },
  postsByUser(username) {
    return request(`/post/user/${encodeURIComponent(username)}`);
  },
  createPost(formData) {
    return request('/post', { method: 'POST', body: formData });
  },
  deletePost(postId) {
    return request(`/post/${postId}`, { method: 'DELETE' });
  },
  setVisibility(postId, visibility) {
    return request(`/post/${postId}/visibility?visibility=${encodeURIComponent(visibility)}`, {
      method: 'PATCH'
    });
  },
  likePost(postId) {
    return request(`/post/${postId}/like`, { method: 'POST' });
  },
  unlikePost(postId) {
    return request(`/post/${postId}/like`, { method: 'DELETE' });
  },
  commentPost(postId, text) {
    return request(`/post/${postId}/comment`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ text })
    });
  },
  replyComment(commentId, text) {
    return request(`/comment/${commentId}/reply`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ text })
    });
  },
  updateComment(commentId, text) {
    return request(`/comment/${commentId}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ text })
    });
  },
  deleteComment(commentId) {
    return request(`/comment/${commentId}`, { method: 'DELETE' });
  },
  likeComment(commentId) {
    return request(`/comment/${commentId}/like`, { method: 'POST' });
  },
  unlikeComment(commentId) {
    return request(`/comment/${commentId}/like`, { method: 'DELETE' });
  },
  follow(username) {
    return request(`/follow/${encodeURIComponent(username)}`, { method: 'POST' });
  },
  acceptFollow(username) {
    return request(`/follow/${encodeURIComponent(username)}/accept`, { method: 'PUT' });
  },
  followRequests() {
    return request('/follow/requests');
  },
  makePublic() {
    return request('/account/public', { method: 'PATCH' });
  },
  makePrivate() {
    return request('/account/private', { method: 'PATCH' });
  },
  createProfile(body) {
    return request('/profile', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body)
    });
  }
};
