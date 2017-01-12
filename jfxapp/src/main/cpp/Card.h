#include <string>

class Card
{
private:
	std::string CardName;
	char Armor;
	char CastCost;
	char Channeling;
	char Life;
	char MaxRange;
	char MinRange;
public:
	Card();
	Card(std::string cardName);
	std::string GetCardName();
	void SetCardName(std::string newVal);
	char GetArmor();
	char GetCastCost();
	char GetChanneling();
	char GetLife();
	char GetMaxRange();
	char GetMinRange();
	void SetArmor(char newVal);
	void SetCastCost(char newVal);
	void SetChanneling(char newVal);
	void SetLife(char newVal);
	void SetMaxRange(char newVal);
	void SetMinRange(char newVal);
};