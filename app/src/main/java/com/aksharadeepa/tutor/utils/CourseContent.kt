package com.aksharadeepa.tutor.utils

import com.aksharadeepa.tutor.models.Chapter
import com.aksharadeepa.tutor.models.QuizQuestion

object CourseContent {
    fun notesFor(title: String): String {
        return when (title) {
            "Electricity" -> "Important ideas\n" +
                    "Electric current is the rate of flow of electric charge. Potential difference pushes charge through a conductor. Ohm's law states V = IR, where V is potential difference, I is current, and R is resistance.\n\n" +
                    "Series circuit\n" +
                    "The same current flows through every resistor. Total resistance is R = R1 + R2 + R3.\n\n" +
                    "Parallel circuit\n" +
                    "The same voltage acts across each branch. Total resistance is found using 1/R = 1/R1 + 1/R2 + 1/R3.\n\n" +
                    "Power and energy\n" +
                    "Electric power P = VI = I2R = V2/R. Commercial electrical energy is measured in kilowatt-hour."
            "Light and Reflection" -> "Important ideas\n" +
                    "Reflection follows two laws: the angle of incidence equals the angle of reflection, and the incident ray, reflected ray, and normal lie in the same plane.\n\n" +
                    "Spherical mirrors\n" +
                    "A concave mirror can form real or virtual images depending on object position. A convex mirror always forms a virtual, erect, diminished image.\n\n" +
                    "Mirror formula\n" +
                    "Use 1/f = 1/v + 1/u and magnification m = -v/u. Follow the sign convention carefully."
            "Acids, Bases and Salts" -> "Important ideas\n" +
                    "Acids release H+ ions in water and turn blue litmus red. Bases release OH- ions and turn red litmus blue.\n\n" +
                    "pH scale\n" +
                    "pH less than 7 is acidic, pH 7 is neutral, and pH greater than 7 is basic. Strong acids have lower pH values.\n\n" +
                    "Neutralisation\n" +
                    "Acid + base gives salt + water. Baking soda, washing soda, plaster of Paris, and bleaching powder are common useful salts."
            "Life Processes" -> "Important ideas\n" +
                    "Life processes are nutrition, respiration, transportation, and excretion. They maintain energy flow and remove waste.\n\n" +
                    "Nutrition\n" +
                    "Plants prepare glucose by photosynthesis using carbon dioxide, water, chlorophyll, and sunlight.\n\n" +
                    "Human systems\n" +
                    "The digestive system breaks food into soluble nutrients. Blood transports oxygen and food. Kidneys filter nitrogenous waste."
            "Metals and Non-metals" -> "Important ideas\n" +
                    "Metals are generally lustrous, malleable, ductile, and good conductors. Non-metals are often brittle and poor conductors, except graphite.\n\n" +
                    "Reactivity\n" +
                    "Reactive metals like sodium and potassium react strongly with water. Metals form basic oxides, while many non-metals form acidic oxides.\n\n" +
                    "Extraction and corrosion\n" +
                    "Less reactive metals can be reduced easily from ores. Rusting of iron requires oxygen and moisture."
            "Our Environment" -> "Important ideas\n" +
                    "An ecosystem includes living organisms and physical surroundings. Producers make food, consumers depend on producers, and decomposers recycle nutrients.\n\n" +
                    "Food chain\n" +
                    "Energy transfer decreases at each trophic level. Only a small part of energy passes to the next level.\n\n" +
                    "Human impact\n" +
                    "Non-biodegradable waste, ozone depletion, and pollution disturb environmental balance."
            "Real Numbers" -> "Important ideas\n" +
                    "Euclid's division lemma says for positive integers a and b, a = bq + r, where 0 <= r < b. It is used to find HCF.\n\n" +
                    "Fundamental theorem of arithmetic\n" +
                    "Every composite number can be expressed as a product of primes in a unique way, apart from order.\n\n" +
                    "Irrational numbers\n" +
                    "Numbers like root 2 and root 3 cannot be written as p/q. Decimal expansions of rational numbers terminate or repeat."
            "Polynomials" -> "Important ideas\n" +
                    "A polynomial in x has terms with non-negative integer powers of x. The degree is the highest power.\n\n" +
                    "Zeros\n" +
                    "A zero of p(x) is a value of x for which p(x) = 0.\n\n" +
                    "Quadratic relationships\n" +
                    "For ax2 + bx + c, sum of zeros = -b/a and product of zeros = c/a."
            "Pair of Linear Equations" -> "Important ideas\n" +
                    "A pair of linear equations in two variables represents two straight lines. They may intersect, be parallel, or coincide.\n\n" +
                    "Methods\n" +
                    "Use substitution, elimination, or cross multiplication to solve.\n\n" +
                    "Consistency\n" +
                    "Intersecting lines have one solution, parallel lines have no solution, and coincident lines have infinitely many solutions."
            "Triangles" -> "Important ideas\n" +
                    "Similar triangles have equal corresponding angles and proportional corresponding sides.\n\n" +
                    "Basic proportionality theorem\n" +
                    "A line drawn parallel to one side of a triangle divides the other two sides in the same ratio.\n\n" +
                    "Pythagoras theorem\n" +
                    "In a right triangle, hypotenuse squared equals the sum of squares of the other two sides."
            "Trigonometry" -> "Important ideas\n" +
                    "Trigonometric ratios connect angles and sides of a right triangle. sin theta = opposite/hypotenuse, cos theta = adjacent/hypotenuse, tan theta = opposite/adjacent.\n\n" +
                    "Identities\n" +
                    "sin2 theta + cos2 theta = 1, 1 + tan2 theta = sec2 theta, and 1 + cot2 theta = cosec2 theta.\n\n" +
                    "Standard angles\n" +
                    "Remember values for 0, 30, 45, 60, and 90 degrees."
            "Statistics" -> "Important ideas\n" +
                    "Statistics helps organise and interpret data. For grouped data, mean can be found by direct, assumed mean, or step deviation method.\n\n" +
                    "Median and mode\n" +
                    "Median is the middle value. Mode is the value that occurs most often.\n\n" +
                    "Ogive\n" +
                    "Cumulative frequency graphs help estimate median visually."
            "The Advent of Europeans" -> "Important ideas\n" +
                    "European trading companies came to India for spices, textiles, and trade profit. The Portuguese arrived first by sea, followed by Dutch, English, and French traders.\n\n" +
                    "Trade to power\n" +
                    "Trading companies built forts, formed armies, and used local political conflicts to expand control.\n\n" +
                    "British expansion\n" +
                    "The Battle of Plassey in 1757 helped the English East India Company gain strong political influence in Bengal."
            "Freedom Movement" -> "Important ideas\n" +
                    "The Indian freedom movement grew from early political associations to mass movements led by national leaders.\n\n" +
                    "Gandhian methods\n" +
                    "Non-cooperation, civil disobedience, and Quit India movements used non-violence and mass participation.\n\n" +
                    "Goal\n" +
                    "The movement united people against colonial rule and demanded self-government."
            "Indian Constitution" -> "Important ideas\n" +
                    "The Constitution is the supreme law of India. It defines the structure of government and protects citizens' rights.\n\n" +
                    "Key values\n" +
                    "The Preamble declares India to be sovereign, socialist, secular, democratic, and republic.\n\n" +
                    "Rights and duties\n" +
                    "Fundamental Rights protect liberty and equality. Fundamental Duties remind citizens of responsibilities."
            "Agriculture in India" -> "Important ideas\n" +
                    "Agriculture depends on soil, rainfall, irrigation, temperature, labour, and markets. India grows food crops and commercial crops.\n\n" +
                    "Crop seasons\n" +
                    "Kharif crops are grown during the monsoon. Rabi crops are grown during winter.\n\n" +
                    "Challenges\n" +
                    "Small landholdings, irregular rainfall, soil erosion, and low storage facilities affect farmers."
            "Natural Resources" -> "Important ideas\n" +
                    "Natural resources include land, water, forests, minerals, soil, and energy resources. They support life and economic activities.\n\n" +
                    "Conservation\n" +
                    "Resources must be used carefully because many are limited or slow to renew.\n\n" +
                    "Sustainable use\n" +
                    "Rainwater harvesting, afforestation, soil conservation, and renewable energy protect future needs."
            "Economics and Development" -> "Important ideas\n" +
                    "Development means improving income, health, education, dignity, and quality of life. Different people may have different development goals.\n\n" +
                    "Indicators\n" +
                    "Per capita income, literacy, life expectancy, and Human Development Index compare development.\n\n" +
                    "Sustainable development\n" +
                    "Economic progress should not destroy resources needed by future generations."
            else -> "$title summary is available offline. Read the key ideas, solve examples, and attempt the quiz."
        }
    }

    fun studyPlanFor(title: String): String {
        return "Quick self-study plan for $title\n\n" +
                "Step 1: Read the notes slowly and underline definitions or formulas.\n" +
                "Step 2: Write five key points without looking.\n" +
                "Step 3: Solve or explain two textbook-style examples.\n" +
                "Step 4: Take the quiz. Re-read the notes for every wrong answer.\n" +
                "Step 5: Mark course complete only when you can explain the chapter to a friend."
    }

    fun makeQuestions(chapters: List<Chapter>): List<QuizQuestion> {
        val list = mutableListOf<QuizQuestion>()
        var qid = 1
        for (chapter in chapters) {
            list.addAll(questionsForChapter(qid, chapter.id, chapter.title))
            qid += 5
        }
        return list
    }

    private fun questionsForChapter(startId: Int, chapterId: Int, title: String): List<QuizQuestion> {
        val q = mutableListOf<QuizQuestion>()
        when (title) {
            "Electricity" -> {
                add(q, startId, chapterId, "According to Ohm's law, what is the relation between V, I and R?", "V = IR", "I = VR", "R = VI", "V = I/R", 0, "Ohm's law is V = IR.")
                add(q, startId + 1, chapterId, "In a series circuit, which quantity is the same through all resistors?", "Current", "Voltage", "Power", "Resistance", 0, "The same current flows through each resistor in series.")
                add(q, startId + 2, chapterId, "What is the SI unit of resistance?", "Ohm", "Volt", "Ampere", "Watt", 0, "Resistance is measured in ohm.")
                add(q, startId + 3, chapterId, "Which formula gives electric power?", "P = VI", "P = V + I", "P = R/I", "P = I/V", 0, "Electric power is voltage multiplied by current.")
                add(q, startId + 4, chapterId, "Commercial electrical energy is commonly measured in", "kilowatt-hour", "newton", "joule per metre", "pascal", 0, "Electricity bills use kilowatt-hour.")
            }
            "Light and Reflection" -> {
                add(q, startId, chapterId, "The angle of incidence is equal to the", "angle of reflection", "focal length", "radius of curvature", "magnification", 0, "This is the first law of reflection.")
                add(q, startId + 1, chapterId, "A convex mirror always forms an image that is", "virtual, erect and diminished", "real and inverted", "real and enlarged", "same size and real", 0, "Convex mirrors form virtual, erect, diminished images.")
                add(q, startId + 2, chapterId, "The mirror formula is", "1/f = 1/v + 1/u", "f = u + v", "m = u/v", "R = f/2", 0, "The spherical mirror formula is 1/f = 1/v + 1/u.")
                add(q, startId + 3, chapterId, "Magnification for a mirror is given by", "m = -v/u", "m = u + v", "m = f/R", "m = uv", 0, "Mirror magnification is m = -v/u.")
                add(q, startId + 4, chapterId, "A concave mirror can form a real image when the object is", "beyond the focus", "behind the mirror", "at the pole only", "at infinity only", 0, "Concave mirrors form real images for many positions beyond focus.")
            }
            "Acids, Bases and Salts" -> {
                add(q, startId, chapterId, "An acid turns blue litmus", "red", "green", "yellow", "blue", 0, "Acids turn blue litmus red.")
                add(q, startId + 1, chapterId, "A solution with pH 7 is", "neutral", "strongly acidic", "strongly basic", "salty only", 0, "pH 7 is neutral.")
                add(q, startId + 2, chapterId, "Neutralisation produces", "salt and water", "acid and oxygen", "base and hydrogen", "metal and water", 0, "Acid plus base gives salt and water.")
                add(q, startId + 3, chapterId, "Bases release which ions in water?", "OH- ions", "H+ ions", "Na+ only", "Cl- only", 0, "Bases release hydroxide ions.")
                add(q, startId + 4, chapterId, "A lower pH value usually means the solution is more", "acidic", "basic", "neutral", "metallic", 0, "Strong acids have lower pH.")
            }
            "Life Processes" -> {
                add(q, startId, chapterId, "Which process allows green plants to prepare food?", "Photosynthesis", "Excretion", "Transpiration only", "Digestion", 0, "Plants make glucose by photosynthesis.")
                add(q, startId + 1, chapterId, "Chlorophyll mainly helps plants to absorb", "light energy", "soil particles", "oxygen only", "nitrogen gas", 0, "Chlorophyll captures sunlight.")
                add(q, startId + 2, chapterId, "Kidneys are mainly involved in", "excretion", "respiration", "digestion", "photosynthesis", 0, "Kidneys filter nitrogenous waste.")
                add(q, startId + 3, chapterId, "Blood transports oxygen with the help of", "haemoglobin", "bile", "starch", "chlorophyll", 0, "Haemoglobin carries oxygen.")
                add(q, startId + 4, chapterId, "Breaking complex food into soluble nutrients is", "digestion", "circulation", "excretion", "reproduction", 0, "Digestion makes food absorbable.")
            }
            "Metals and Non-metals" -> {
                add(q, startId, chapterId, "Metals are generally", "malleable and ductile", "brittle and dull", "poor conductors", "gases at room temperature", 0, "Most metals can be beaten into sheets and drawn into wires.")
                add(q, startId + 1, chapterId, "Which non-metal conducts electricity?", "Graphite", "Sulphur", "Phosphorus", "Iodine", 0, "Graphite is a conducting form of carbon.")
                add(q, startId + 2, chapterId, "Rusting of iron requires oxygen and", "moisture", "nitrogen", "sand", "kerosene", 0, "Moist air causes rusting.")
                add(q, startId + 3, chapterId, "Metal oxides are generally", "basic", "acidic", "neutral gases", "salts only", 0, "Many metal oxides are basic.")
                add(q, startId + 4, chapterId, "Sodium and potassium are stored in kerosene because they", "react strongly with air and water", "are very heavy", "are non-metals", "do not conduct heat", 0, "Kerosene prevents dangerous reaction.")
            }
            "Our Environment" -> {
                add(q, startId, chapterId, "Producers in an ecosystem are usually", "green plants", "carnivores", "decomposers only", "parasites", 0, "Green plants produce food.")
                add(q, startId + 1, chapterId, "Energy transfer in a food chain", "decreases at each trophic level", "increases at each level", "stays exactly same", "stops at producers", 0, "Only a small part of energy passes forward.")
                add(q, startId + 2, chapterId, "Decomposers help by", "recycling nutrients", "making plastic", "blocking food chains", "destroying sunlight", 0, "Decomposers return nutrients to soil.")
                add(q, startId + 3, chapterId, "Non-biodegradable waste is harmful because it", "persists for a long time", "quickly becomes manure", "is always natural", "contains only water", 0, "It does not break down easily.")
                add(q, startId + 4, chapterId, "Ozone layer mainly protects living beings from", "ultraviolet radiation", "sound", "rain", "gravity", 0, "Ozone absorbs harmful UV rays.")
            }
            else -> addGeneric(q, startId, chapterId, title)
        }
        return q
    }

    private fun addGeneric(q: MutableList<QuizQuestion>, startId: Int, chapterId: Int, title: String) {
        val notes = notesFor(title)
        when (title) {
            "Real Numbers" -> {
                add(q, startId, chapterId, "Euclid's division lemma is written as", "a = bq + r, 0 <= r < b", "a = b + q + r", "r = aq + b", "b = ar", 0, "This is the standard form of Euclid's division lemma.")
                add(q, startId + 1, chapterId, "Every composite number can be expressed uniquely as a product of", "prime numbers", "decimals", "fractions only", "negative numbers only", 0, "This is the fundamental theorem of arithmetic.")
                add(q, startId + 2, chapterId, "The decimal expansion of a rational number is", "terminating or repeating", "always non-repeating", "never terminating", "always integer", 0, "Rational numbers terminate or repeat.")
                add(q, startId + 3, chapterId, "HCF can be found efficiently using", "Euclid's algorithm", "Pythagoras theorem", "mirror formula", "pH scale", 0, "Euclid's algorithm repeatedly applies division lemma.")
                add(q, startId + 4, chapterId, "Root 2 is an example of an", "irrational number", "even number", "integer", "natural number", 0, "Root 2 cannot be written as p/q.")
            }
            "Polynomials" -> {
                add(q, startId, chapterId, "A zero of p(x) is a value where p(x) is", "0", "1", "negative only", "undefined always", 0, "A zero makes the polynomial equal zero.")
                add(q, startId + 1, chapterId, "The degree of a polynomial is the", "highest power of variable", "lowest coefficient", "number of zeros only", "constant term", 0, "Degree is highest exponent.")
                add(q, startId + 2, chapterId, "For ax2 + bx + c, sum of zeros is", "-b/a", "c/a", "a/b", "b/c", 0, "Sum of zeros equals -b/a.")
                add(q, startId + 3, chapterId, "For ax2 + bx + c, product of zeros is", "c/a", "-b/a", "a+c", "b/a", 0, "Product of zeros equals c/a.")
                add(q, startId + 4, chapterId, "A quadratic polynomial has degree", "2", "1", "3", "0", 0, "Quadratic means degree two.")
            }
            "Pair of Linear Equations" -> {
                add(q, startId, chapterId, "Two intersecting lines have", "one solution", "no solution", "infinite solutions always", "no variables", 0, "The point of intersection is the solution.")
                add(q, startId + 1, chapterId, "Parallel lines represent equations with", "no solution", "one solution", "infinite solutions", "only zero solution", 0, "Parallel lines do not meet.")
                add(q, startId + 2, chapterId, "Coincident lines have", "infinitely many solutions", "no solution", "one solution", "two solutions only", 0, "Every point on the line satisfies both.")
                add(q, startId + 3, chapterId, "Which is a method to solve linear equations?", "Elimination", "Photosynthesis", "Neutralisation", "Rusting", 0, "Elimination removes one variable.")
                add(q, startId + 4, chapterId, "A linear equation in two variables represents a", "straight line", "circle only", "triangle only", "food chain", 0, "Its graph is a line.")
            }
            "Triangles" -> {
                add(q, startId, chapterId, "Similar triangles have corresponding angles", "equal", "always unequal", "right angles only", "zero", 0, "Similarity keeps angles equal.")
                add(q, startId + 1, chapterId, "In similar triangles, corresponding sides are", "proportional", "always equal", "never related", "negative", 0, "Sides are in the same ratio.")
                add(q, startId + 2, chapterId, "Pythagoras theorem applies to", "right triangles", "all quadrilaterals", "circles", "food chains", 0, "It applies to right-angled triangles.")
                add(q, startId + 3, chapterId, "Basic proportionality theorem uses a line parallel to", "one side of a triangle", "a circle radius", "a mirror", "a current", 0, "The parallel line divides two sides proportionally.")
                add(q, startId + 4, chapterId, "In a right triangle, the longest side is the", "hypotenuse", "median", "altitude", "base only", 0, "The side opposite the right angle is hypotenuse.")
            }
            "Trigonometry" -> {
                add(q, startId, chapterId, "sin theta equals", "opposite/hypotenuse", "adjacent/hypotenuse", "opposite/adjacent", "hypotenuse/opposite", 0, "Sine is opposite over hypotenuse.")
                add(q, startId + 1, chapterId, "cos theta equals", "adjacent/hypotenuse", "opposite/hypotenuse", "opposite/adjacent", "hypotenuse/adjacent", 0, "Cosine is adjacent over hypotenuse.")
                add(q, startId + 2, chapterId, "tan theta equals", "opposite/adjacent", "adjacent/hypotenuse", "hypotenuse/opposite", "adjacent/opposite", 0, "Tangent is opposite over adjacent.")
                add(q, startId + 3, chapterId, "sin2 theta + cos2 theta equals", "1", "0", "tan theta", "sec theta", 0, "This is a basic identity.")
                add(q, startId + 4, chapterId, "Trigonometric ratios are studied in a", "right triangle", "food chain", "constitution", "salt solution", 0, "They relate sides and angles of right triangles.")
            }
            "Statistics" -> {
                add(q, startId, chapterId, "The median is the", "middle value", "highest value always", "lowest value always", "sum of all values", 0, "Median lies in the middle of ordered data.")
                add(q, startId + 1, chapterId, "The mode is the value that occurs", "most often", "least often", "first only", "last only", 0, "Mode is most frequent.")
                add(q, startId + 2, chapterId, "Grouped data mean can be found by", "assumed mean method", "mirror method", "pH method", "food chain method", 0, "Assumed mean is a standard statistics method.")
                add(q, startId + 3, chapterId, "An ogive is a graph of", "cumulative frequency", "electric current", "pH only", "reflection only", 0, "Ogives use cumulative frequencies.")
                add(q, startId + 4, chapterId, "Statistics mainly helps to", "organise and interpret data", "produce salts", "measure voltage only", "form images only", 0, "Statistics studies data.")
            }
            "The Advent of Europeans" -> {
                add(q, startId, chapterId, "The Portuguese came to India mainly for", "trade", "printing exams", "building dams", "growing wheat only", 0, "European companies came for trade profit.")
                add(q, startId + 1, chapterId, "The Battle of Plassey was fought in", "1757", "1857", "1947", "1526", 0, "Plassey in 1757 strengthened Company power in Bengal.")
                add(q, startId + 2, chapterId, "European companies built forts mainly to protect", "trade settlements", "forests", "monsoons", "schools only", 0, "Forts protected trading centres.")
                add(q, startId + 3, chapterId, "The English East India Company first came as a", "trading company", "village panchayat", "farmers union", "court only", 0, "It began with trade.")
                add(q, startId + 4, chapterId, "Spices and textiles attracted Europeans because they were", "profitable trade goods", "not used anywhere", "free goods", "only local waste", 0, "They had high market value.")
            }
            "Freedom Movement" -> {
                add(q, startId, chapterId, "Gandhian movements mainly used", "non-violence", "dictatorship", "foreign rule", "forced labour", 0, "Non-violence was central to Gandhian struggle.")
                add(q, startId + 1, chapterId, "Quit India Movement demanded", "end of British rule", "more salt tax", "more imports", "division of villages", 0, "It called for British withdrawal.")
                add(q, startId + 2, chapterId, "Civil disobedience means", "peacefully breaking unjust laws", "obeying every unjust law", "ending elections", "stopping education", 0, "It protests unjust laws.")
                add(q, startId + 3, chapterId, "The freedom movement united people against", "colonial rule", "rainfall", "mathematics", "food crops", 0, "The struggle opposed British colonial rule.")
                add(q, startId + 4, chapterId, "Non-cooperation encouraged Indians to boycott", "British institutions and goods", "all farming", "all languages", "all families", 0, "Boycott was part of non-cooperation.")
            }
            "Indian Constitution" -> {
                add(q, startId, chapterId, "The Constitution is the supreme", "law of India", "crop of India", "river of India", "market of India", 0, "It is the highest legal document.")
                add(q, startId + 1, chapterId, "The Preamble describes India as a", "sovereign democratic republic", "private company", "foreign colony", "single village", 0, "The Preamble states core constitutional values.")
                add(q, startId + 2, chapterId, "Fundamental Rights protect", "liberty and equality", "only taxes", "only crops", "only minerals", 0, "Rights protect citizens.")
                add(q, startId + 3, chapterId, "Secular means the state treats religions", "equally", "as illegal", "as companies", "as crops", 0, "Secularism means equal respect for religions.")
                add(q, startId + 4, chapterId, "Fundamental Duties remind citizens of", "responsibilities", "only income", "only rainfall", "only imports", 0, "Duties are citizen responsibilities.")
            }
            "Agriculture in India" -> {
                add(q, startId, chapterId, "Kharif crops are mainly grown during", "monsoon", "deep winter only", "summer vacation only", "no rainfall", 0, "Kharif season depends on monsoon.")
                add(q, startId + 1, chapterId, "Rabi crops are mainly grown during", "winter", "monsoon only", "April heat only", "no season", 0, "Rabi crops are winter crops.")
                add(q, startId + 2, chapterId, "Agriculture depends strongly on", "soil and rainfall", "mirrors", "pH paper only", "electric circuits", 0, "Soil and rainfall are major factors.")
                add(q, startId + 3, chapterId, "A common challenge for Indian farmers is", "irregular rainfall", "too many mirrors", "no sunlight anywhere", "absence of soil in India", 0, "Rainfall uncertainty affects crops.")
                add(q, startId + 4, chapterId, "Food crops include", "rice and wheat", "iron and copper", "coal and mica", "salt and acid", 0, "Rice and wheat are food crops.")
            }
            "Natural Resources" -> {
                add(q, startId, chapterId, "Natural resources include", "land, water and forests", "only plastic", "only machines", "only exams", 0, "They are resources from nature.")
                add(q, startId + 1, chapterId, "Afforestation helps conserve", "forests and soil", "mirrors", "equations", "salts only", 0, "Trees protect soil and forests.")
                add(q, startId + 2, chapterId, "Rainwater harvesting is used to conserve", "water", "electric charge", "polynomials", "litmus", 0, "It stores rainwater.")
                add(q, startId + 3, chapterId, "Sustainable use means using resources without harming", "future needs", "only today marks", "all rainfall", "all equations", 0, "Future generations also need resources.")
                add(q, startId + 4, chapterId, "Renewable energy is useful because it", "can be replenished naturally", "never comes from nature", "is always plastic", "is only salt", 0, "Renewable sources refill naturally.")
            }
            "Economics and Development" -> {
                add(q, startId, chapterId, "Development includes income, health and", "education", "only mirror images", "only rusting", "only pH", 0, "Human development includes education.")
                add(q, startId + 1, chapterId, "Per capita income means", "average income per person", "total rainfall", "number of crops only", "area of triangle", 0, "It is total income divided by population.")
                add(q, startId + 2, chapterId, "Different people may have", "different development goals", "same goals always", "no goals", "only one job", 0, "Development goals vary.")
                add(q, startId + 3, chapterId, "HDI compares development using health, education and", "income", "mirror size", "metal colour", "food chain length", 0, "HDI includes income.")
                add(q, startId + 4, chapterId, "Sustainable development protects resources for", "future generations", "only one day", "only one student", "no one", 0, "It balances present and future needs.")
            }
            else -> {
                add(q, startId, chapterId, "Which statement is central to $title?", firstSentence(notes), "It is unrelated to SSLC study.", "It removes all revision.", "It has no examples.", 0, "The answer comes directly from the chapter notes.")
                add(q, startId + 1, chapterId, "What should you do after reading $title?", "Write key points and take the quiz", "Skip revision", "Delete progress", "Avoid examples", 0, "Practice and quiz review improve mastery.")
                add(q, startId + 2, chapterId, "$title should be marked complete when you can", "explain the main ideas", "only open the page", "guess answers", "close the app", 0, "Completion should mean understanding.")
                add(q, startId + 3, chapterId, "Wrong quiz answers should be used to", "revise weak points", "ignore the chapter", "reduce study time", "remove notes", 0, "Review helps learning.")
                add(q, startId + 4, chapterId, "The course content is stored", "offline on the device", "on a cloud server", "through Firebase", "through internet login", 0, "The app is offline-first.")
            }
        }
    }

    private fun firstSentence(notes: String): String {
        val dot = notes.indexOf('.')
        return if (dot > 0) notes.substring(0, dot + 1).replace("\n", " ") else notes
    }

    private fun add(q: MutableList<QuizQuestion>, id: Int, chapterId: Int, question: String, a: String, b: String, c: String, d: String, correct: Int, explanation: String) {
        q.add(QuizQuestion(id, chapterId, question, a, b, c, d, correct, explanation))
    }
}
