module SSCHUUACTest
  module AndroidHelpers
    def enter_text(uiquery, text)
      query(uiquery, {:setText => text})
    end
  end
end

World(SSCHUUACTest::AndroidHelpers)
