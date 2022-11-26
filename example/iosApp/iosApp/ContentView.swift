import SwiftUI
import shared

struct ContentView: View {
    init() {
        PlatformKt.initialize()
    }

	var body: some View {
		Text(Greeting().greeting())
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
