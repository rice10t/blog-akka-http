import Link from 'next/link'

export default function Index() {
  return (
    <div>
      <a href="https://github.com/login/oauth/authorize?client_id=b5f32d2eb83bf41868d5">Login</a>
      <br/>
      <Link href="/settings/edit">
        <a>Settings</a>
      </Link>
    </div>
  )
}
