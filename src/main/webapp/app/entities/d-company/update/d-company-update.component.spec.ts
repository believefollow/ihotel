jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DCompanyService } from '../service/d-company.service';
import { IDCompany, DCompany } from '../d-company.model';

import { DCompanyUpdateComponent } from './d-company-update.component';

describe('Component Tests', () => {
  describe('DCompany Management Update Component', () => {
    let comp: DCompanyUpdateComponent;
    let fixture: ComponentFixture<DCompanyUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let dCompanyService: DCompanyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DCompanyUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DCompanyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DCompanyUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      dCompanyService = TestBed.inject(DCompanyService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const dCompany: IDCompany = { id: 456 };

        activatedRoute.data = of({ dCompany });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(dCompany));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dCompany = { id: 123 };
        spyOn(dCompanyService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dCompany });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dCompany }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(dCompanyService.update).toHaveBeenCalledWith(dCompany);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dCompany = new DCompany();
        spyOn(dCompanyService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dCompany });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dCompany }));
        saveSubject.complete();

        // THEN
        expect(dCompanyService.create).toHaveBeenCalledWith(dCompany);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dCompany = { id: 123 };
        spyOn(dCompanyService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dCompany });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(dCompanyService.update).toHaveBeenCalledWith(dCompany);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
