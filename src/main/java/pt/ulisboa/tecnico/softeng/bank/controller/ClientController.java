package pt.ulisboa.tecnico.softeng.bank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.bank.domain.Bank;
import pt.ulisboa.tecnico.softeng.bank.domain.Client;
import pt.ulisboa.tecnico.softeng.bank.dto.ClientDto;
import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

@Controller
@RequestMapping(value = "/banks/bank/{code}/clients")
public class ClientController {
	private static Logger logger = LoggerFactory.getLogger(ClientController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String clientForm(Model model, @PathVariable String code) {
		logger.info("clientForm");
		model.addAttribute("client", new ClientDto());
		model.addAttribute("bank", Bank.getBankByCode(code));
		return "bankView";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String clientSubmit(Model model,  @PathVariable String code, @ModelAttribute ClientDto client) {
		logger.info("clientSubmit id:{}, name:{}, age:{}", client.getId(), client.getName(), client.getAge());

		try {
			new Client(Bank.getBankByCode(code), client.getId(), client.getName(), client.getAge());
		} catch (BankException be) {
			model.addAttribute("error", "Error: it was not possible to create the client");
			model.addAttribute("client", client);
			model.addAttribute("bank", Bank.getBankByCode(code));
			return "bankView";
		}

		return "redirect:/banks/bank/" + code + "/clients";
	}

	/*@RequestMapping(value = "/bank/{code}", method = RequestMethod.GET)
	public String showBank(Model model, @PathVariable String code) {
		logger.info("showBank code:{}", code);

		Bank bank = Bank.getBankByCode(code);
		
		model.addAttribute("bank", bank);
		return "bankView";
	}*/
}
