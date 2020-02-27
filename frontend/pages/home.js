import { useEffect } from "react"

export default function Home() {
  useEffect(() => {
    fetch("http://localhost:8080/home", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      mode: "cors",
    })
      .then(console.log)
      .catch(console.log)
  }, [])

  return <div>home</div>
}
