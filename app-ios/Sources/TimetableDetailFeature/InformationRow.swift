import SwiftUI

struct InformationRow: View {
    private let icon: Image
    private let title: String
    private let content: String

    init(
        icon: Image,
        title: String,
        content: String
    ) {
        self.icon = icon
        self.title = title
        self.content = content
    }

    var body: some View {
        HStack {
            icon
            HStack(spacing: 12) {
                Text(title)
                    .font(.callout)
                    .foregroundStyle(Color(.surfaceVariant))
                    .bold()
                HStack {
                    Text(content)
                        .font(.callout)
                        .foregroundStyle(Color(.onSurface))
                }

            }
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .foregroundStyle(
            Color.white
        )
    }
}

#Preview {
    InformationRow(
        icon: Image(systemName: "clock"),
        title: String(localized: "TimeTableDetailDate", bundle: .module),
        content: SampleData.dateValue
    )
}
