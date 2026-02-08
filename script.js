const el = id => document.getElementById(id)

const STORAGE_KEY = "profile_data"

const inputs = {
  name: el("nameInput"),
  bio: el("bioInput"),
  likes: el("likesInput"),
  dislikes: el("dislikesInput"),
  emojis: el("emojiInput"),
  links: el("linksInput"),
  banner: el("bannerInput"),
  avatar: el("avatarInput")
}

let bannerSrc = "banner.jpg"
let avatarSrc = "avatar.jpg"

function render(data){
  el("displayName").textContent = data.name || ""
  el("bioText").textContent = data.bio || ""
  el("likesText").textContent = data.likes || ""
  el("dislikesText").textContent = data.dislikes || ""
  el("emojiRow").textContent = data.emojis || ""

  el("bannerImg").src = data.bannerSrc
  el("avatarImg").src = data.avatarSrc

  el("linksBox").innerHTML = ""
  ;(data.links || "").split("\n").forEach(l=>{
    const p=l.split("|")
    if(p.length===2){
      const a=document.createElement("a")
      a.textContent=p[0].trim()
      a.href=p[1].trim()
      a.target="_blank"
      el("linksBox").appendChild(a)
    }
  })

  const box = el("topArtistsBox")
  box.innerHTML = ""
  const artists = data.topArtists && data.topArtists.length
    ? data.topArtists
    : ["(connect Spotify to load artists)"]

  artists.forEach(name=>{
    const d = document.createElement("div")
    d.textContent = name
    box.appendChild(d)
  })
}

function getData(){
  return {
    name: inputs.name.value,
    bio: inputs.bio.value,
    likes: inputs.likes.value,
    dislikes: inputs.dislikes.value,
    emojis: inputs.emojis.value,
    links: inputs.links.value,
    bannerSrc,
    avatarSrc,
    topArtists: []
  }
}

function save(data){
  localStorage.setItem(STORAGE_KEY, JSON.stringify(data))
}

function load(){
  const raw = localStorage.getItem(STORAGE_KEY)
  return raw ? JSON.parse(raw) : null
}

el("saveBtn").onclick = () => {
  const data = getData()
  save(data)
  render(data)
  el("editor").style.display = "none"
}

el("editBtn").onclick = () => {
  const data = load()
  if(data){
    if(data.name) inputs.name.value = data.name
    if(data.bio) inputs.bio.value = data.bio
    if(data.likes) inputs.likes.value = data.likes
    if(data.dislikes) inputs.dislikes.value = data.dislikes
    if(data.emojis) inputs.emojis.value = data.emojis
    if(data.links) inputs.links.value = data.links
    bannerSrc = data.bannerSrc || bannerSrc
    avatarSrc = data.avatarSrc || avatarSrc
  }
  el("editor").style.display = "block"
}

inputs.banner.onchange = e=>{
  bannerSrc = URL.createObjectURL(e.target.files[0])
}

inputs.avatar.onchange = e=>{
  avatarSrc = URL.createObjectURL(e.target.files[0])
}

const saved = load()
if(saved){
  render(saved)
  el("editor").style.display = "none"
}
